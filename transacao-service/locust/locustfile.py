from locust import HttpUser, task, between
import random
from datetime import date

class TransacaoUser(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):

        descricao = f"Transacao_{random.randint(1000, 9999)}"
        self.novo_produto_payload = {
            "valor": round(random.uniform(10.0, 500.0), 2),
            "tipo": f"CREDITO",
            "descricao": descricao,
            "contexto": f"contexto_{random.randint(1000, 9999)}",
            "canal": f"canal_{random.randint(1000, 9999)}",
            "cartao_id": random.randint(1, 100),
            "pedido_id": random.randint(1, 100)
        }

        response = self.client.post("/transacoes", json=self.novo_produto_payload)
        if response.status_code == 201:
            self.transacao_id = response.json().get("id")
        else:
            self.transacao_id = 1

    @task(3)
    def cadastrar_transacao(self):
        descricao = f"Transacao_{random.randint(1000, 9999)}"
        payload = {
            "valor": round(random.uniform(10.0, 500.0), 2),
            "tipo": f"CREDITO",
            "descricao": descricao,
            "contexto": f"contexto_{random.randint(1000, 9999)}",
            "canal": f"canal_{random.randint(1000, 9999)}",
            "cartao_id": random.randint(1, 100),
            "pedido_id": random.randint(1, 100)
        }
        self.client.post("/transacoes", json=payload)

    @task(5)
    def listar_pendentes(self):
        self.client.get(f"/transacoes/pendentes/{self.cartao_id}")

    @task(2)
    def atualizar_transacao(self):
        payload = {
            "isPago": true
        }
        self.client.put(f"/transacoes/atualizar/{self.transacao_id}", json=payload)