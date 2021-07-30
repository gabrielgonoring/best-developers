# Visualizar app publicado

O projeto foi publicado e para acessar basta acessar as seguintes urls:

* Aplicação: https://best-developers-frontend.herokuapp.com/
* Documentação da api: https://best-developers-api.herokuapp.com/swagger-ui/

Obs: O projeto demora aproximadamente 30 segundo para iniciar caso esteja ocioso por algum tempo

# Iniciar a aplicação

Caso deseja fazer funcionar a aplicação em sua máquina local basta seguir os seguintes passos:

* Instale o [docker](https://www.docker.com/) em sua máquina 
* Clonar o projeto com o comando: git clone https://github.com/gabrielgonoring/best-developers.git
* Abra o terminal dentro da pasta do projeto que acabou de ser clonado
* Dentro da pasta do projeto execute o comando: docker-compose build
* Após executar o comando acima, execute o seguinte comando: docker-compose up

Após seguir esses passo o projeto estará acessível em sua máquina local, sendo que a documentação da api pode ser acessada pelo endereço http://localhost:8080/swagger-ui/ a aplicação web (react) está disponível na url: http://localhost:3000/