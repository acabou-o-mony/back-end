from locust import HttpUser, task, between
import random

class CarrinhoUser(HttpUser):
    wait_time = between(1, 3)  # tempo de espera entre as tarefas

    # IDs simulados (em um cenário real, você pode randomizar ou integrar com seu banco/dados reais)
    carrinho_ids = [1, 2, 3]
    produto_ids = [1, 2, 3]

    @task(1)
    def adicionar_ao_carrinho(self):
        payload = {
            "idCarrinho": random.choice(self.carrinho_ids),
            "produtoId": random.choice(self.produto_ids),
            "quantidade": random.randint(1, 5)
        }
        self.client.post("/carrinhos", json=payload)

    @task(2)
    def listar_carrinho(self):
        id_carrinho = random.choice(self.carrinho_ids)
        self.client.get(f"/carrinhos/{id_carrinho}")

    @task(1)
    def atualizar_quantidade(self):
        id_carrinho = random.choice(self.carrinho_ids)
        id_produto = random.choice(self.produto_ids)
        payload = {
            "idCarrinho": id_carrinho,
            "produtoId": id_produto,
            "quantidade": random.randint(1, 10)
        }
        self.client.put(f"/carrinhos/{id_carrinho}/produtos/{id_produto}", json=payload)

    @task(1)
    def deletar_produto(self):
        id_carrinho = random.choice(self.carrinho_ids)
        id_produto = random.choice(self.produto_ids)
        self.client.delete(f"/carrinhos/{id_carrinho}/produtos/{id_produto}")
