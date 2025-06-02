from locust import HttpUser, task, between
import random
from datetime import date

class ProdutoUser(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):

        nome = f"Produto_{random.randint(1000, 9999)}"
        self.novo_produto_payload = {
            "nome": nome,
            "descricao": f"Descrição do {nome}",
            "precoUnitario": round(random.uniform(10.0, 500.0), 2),
            "estoque": random.randint(1, 100),
            "ativo": True,
            "dataCriacao": str(date.today())
        }

        response = self.client.post("/produtos", json=self.novo_produto_payload)
        if response.status_code == 201:
            self.produto_id = response.json().get("id")
        else:
            self.produto_id = 1

    @task(3)
    def cadastrar_produto(self):
        nome = f"Produto_{random.randint(10000, 99999)}"
        payload = {
            "nome": nome,
            "descricao": f"Descrição do {nome}",
            "precoUnitario": round(random.uniform(10.0, 500.0), 2),
            "estoque": random.randint(1, 100),
            "ativo": True,
            "dataCriacao": str(date.today())
        }
        self.client.post("/produtos", json=payload)

    @task(5)
    def listar_produtos(self):
        self.client.get("/produtos")

    @task(3)
    def listar_ativos(self):
        self.client.get("/produtos/ativos")

    @task(4)
    def buscar_por_id(self):
        self.client.get(f"/produtos/{self.produto_id}")

    @task(4)
    def buscar_por_nome(self):
        self.client.get("/produtos/nomes/Produto")

    @task(2)
    def atualizar_produto(self):
        payload = {
            "estoque": random.randint(0, 200),
            "ativo": random.choice([True, False])
        }
        self.client.put(f"/produtos/{self.produto_id}", json=payload)

    @task(1)
    def deletar_produto(self):
        id_para_deletar = self.produto_id
        self.client.delete(f"/produtos/{id_para_deletar}")
