from locust import HttpUser, task, between
import random

class TransacaoUser(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        self.transacao_id = None
        self.cartao_id = random.randint(1, 100)
        self.pedido_id = random.randint(1, 100)

    @task(5)
    def cadastrar_transacao(self):
        self.cartao_id = random.randint(1, 100)
        self.pedido_id = random.randint(1, 100)

        descricao = f"Transacao_{random.randint(1000, 9999)}"
        payload = {
            "valor": round(random.uniform(10.0, 500.0), 2),
            "tipo": "CREDITO",
            "descricao": descricao,
            "contexto": f"contexto_{random.randint(1000, 9999)}",
            "canal": f"canal_{random.randint(1000, 9999)}",
            "cartaoId": self.cartao_id,
            "pedidoId": self.pedido_id
        }
        response = self.client.post("/transacoes", json=payload)
        if response.status_code == 201:
            self.transacao_id = response.json().get("id")

    @task(3)
    def listar_pendentes(self):
        cartao = self.cartao_id if self.cartao_id else random.randint(1, 100)
        self.client.get(f"/transacoes/pendentes/{cartao}")

    @task(2)
    def atualizar_transacao(self):
        if self.transacao_id:
            payload = {"isPago": True}
            self.client.put(f"/transacoes/atualizar/{self.transacao_id}", json=payload)