document.addEventListener("DOMContentLoaded", function () {

    // Função para buscar todos os produtos
    async function fetchProducts() {
        try {
            const response = await fetch("http://localhost:8080/api/v1/produtos");
            if (!response.ok) {
                throw new Error(`Erro de rede: ${response.status}`);
            }

            const data = await response.json();
            const products = data.produtos;

            displayProducts(products);
        } catch (error) {
            displayMessage(error.message, 'error');
        }
    }

    // Função para exibir os produtos na tela
    function displayProducts(products) {
        const productList = document.getElementById("product-list");
        productList.innerHTML = ''; 

        if (products.length === 0) {
            productList.innerHTML = '<p>Nenhum produto encontrado.</p>';
            return;
        }

        products.forEach(product => {
            const productDiv = document.createElement("div");
            productDiv.className = "product-item";
            productDiv.innerHTML = `
                <span>ID: ${product.id}</span>
                <span>Nome: ${product.nome}</span>
                <span>Preço: R$ ${product.preco.toFixed(2)}</span>
                <button class="edit-btn" data-id="${product.id}">Editar</button>
                <button class="delete-btn" data-id="${product.id}">Deletar</button>
            `;
            productList.appendChild(productDiv);
        });

        // Adiciona o listener para o botão de edição
        const editButtons = document.querySelectorAll('.edit-btn');
        editButtons.forEach(button => {
            button.addEventListener('click', function () {
                const productId = button.getAttribute('data-id');
                editProduct(productId);  // Chama a função de edição passando o id do produto
            });
        });

        // Adiciona o listener para o botão de exclusão
        const deleteButtons = document.querySelectorAll('.delete-btn');
        deleteButtons.forEach(button => {
            button.addEventListener('click', function () {
                const productId = button.getAttribute('data-id');
                deleteProduct(productId);  // Chama a função de exclusão passando o id do produto
            });
        });
    }

    // Função para editar produto
    async function editProduct(productId) {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/produtos/${productId}`);
            const data = await response.json();

            // Preenche o formulário com os dados do produto
            if (data.status === 200) {
                const product = data.produto;
                document.getElementById("product-name").value = product.nome;
                document.getElementById("product-price").value = product.preco;
                document.getElementById("product-form").dataset.productId = product.id;
                document.getElementById("form-title").textContent = "Editar Produto";
                document.getElementById("save-button").textContent = "Salvar Alterações";
            } else {
                displayMessage(data.mensagem, 'error');
            }
        } catch (error) {
            displayMessage(error.message, 'error');
        }
    }

    // Função para excluir produto
    async function deleteProduct(productId) {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/produtos/${productId}`, {
                method: 'DELETE',
            });

            const data = await response.json();
            if (data.status === 200) {
                displayMessage(data.mensagem, 'success');
                fetchProducts(); // Recarrega a lista de produtos
            } else {
                displayMessage(data.mensagem, 'error');
            }
        } catch (error) {
            displayMessage(error.message, 'error');
        }
    }

    // Função para salvar um novo produto ou atualizar um produto existente
    async function saveProduct(event) {
        event.preventDefault();

        const name = document.getElementById("product-name").value;
        const price = parseFloat(document.getElementById("product-price").value).toFixed(1);
        const floatPrice = parseFloat(price);

        if (isNaN(floatPrice)) {
            displayMessage("Por favor, insira um valor válido para o preço.", 'error');
            return;
        }

        const productId = document.getElementById("product-form").dataset.productId;
        const method = productId ? 'PUT' : 'POST';
        const url = productId ? `http://localhost:8080/api/v1/produtos/${productId}` : `http://localhost:8080/api/v1/produtos`;

        const productData = {
            nome: name,
            preco: floatPrice,
        };

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(productData)
            });

            const data = await response.json();
            if (data.status === 200 || data.status === 201) {
                displayMessage('Produto salvo com sucesso!', 'success');
                fetchProducts(); // Recarrega a lista de produtos
                clearForm();
            } else {
                displayMessage(data.mensagem, 'error');
            }
        } catch (error) {
            displayMessage(error.message, 'error');
        }
    }

    function displayMessage(message, type) {
        const messageContainer = document.getElementById("message");
        messageContainer.textContent = message;
        messageContainer.className = type === 'error' ? 'error' : 'success';
        messageContainer.classList.remove('hidden');
        setTimeout(() => {
            messageContainer.classList.add('hidden');
        }, 2000);
    }
    

    function clearForm() {
        document.getElementById("product-name").value = '';
        document.getElementById("product-price").value = '';
        document.getElementById("product-form").dataset.productId = '';
        document.getElementById("form-title").textContent = "Adicionar Produto";
        document.getElementById("save-button").textContent = "Salvar Produto";
    }

    document.getElementById("product-form").addEventListener("submit", saveProduct);

    fetchProducts();
    
});

