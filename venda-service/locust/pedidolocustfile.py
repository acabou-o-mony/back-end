from locust import HttpUser, task, between
import random

class PedidoCarrinhoUser(HttpUser):
    wait_time = between(1, 3)  # tempo de espera entre as tarefas

    # IDs simulados (em um cenário real, você pode randomizar ou integrar com seu banco/dados reais)
    carrinho_ids = [1, 2, 3]  # Certifique-se de que esses carrinhos existam no banco
    produto_ids = [1, 2, 3]  # Certifique-se de que esses produtos existam
    conta_ids = [1, 2, 3]  # Certifique-se de que essas contas existam

    @task(1)
    def adicionar_ao_carrinho(self):
        payload = {
            "idCarrinho": random.choice(self.carrinho_ids),
            "produtoId": random.choice(self.produto_ids),
            "quantidade": random.randint(1, 5)
        }
        # Adiciona um produto ao carrinho
        self.client.post("/carrinhos", json=payload)

    @task(2)
    def listar_carrinho(self):
        id_carrinho = random.choice(self.carrinho_ids)
        # Lista os produtos do carrinho
        self.client.get(f"/carrinhos/{id_carrinho}")

    @task(1)
    def finalizar_pedido(self):
        # Simulando o carrinho e conta
        id_carrinho = random.choice(self.carrinho_ids)
        id_conta = random.choice(self.conta_ids)

        # Finalizando o pedido com a URL correta
        self.client.post(f"/pedidos/finalizar/{id_carrinho}?idConta={id_conta}")

    @task(1)
    def listar_pedidos(self):
        id_conta = random.choice(self.conta_ids)
        # Lista os pedidos feitos pela conta
        self.client.get(f"/pedidos?idConta={id_conta}")

    @task(1)
    def atualizar_quantidade(self):
        id_carrinho = random.choice(self.carrinho_ids)
        id_produto = random.choice(self.produto_ids)
        payload = {
            "quantidade": random.randint(1, 10)
        }
        # Atualiza a quantidade de um produto no carrinho
        self.client.put(f"/carrinhos/{id_carrinho}/produtos/{id_produto}", json=payload)

    @task(1)
    def deletar_produto(self):
        id_carrinho = random.choice(self.carrinho_ids)
        id_produto = random.choice(self.produto_ids)
        # Deleta um produto do carrinho
        self.client.delete(f"/carrinhos/{id_carrinho}/produtos/{id_produto}")
