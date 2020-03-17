# Star Wars API - Java [![](https://travis-ci.org/bmdefreitas/star-wars-api-java.svg?branch=master)](https://travis-ci.com/bmdefreitas/star-wars-api-java)

REST API Star Wars com Java (Spring Boot), Hateoas, Hystrix e Cache

## Iniciando com Docker

Clonar o repositório

```
git clone https://github.com/bmdefreitas/star-wars-api-java.git
```

Package do Projeto
```
./mvnw package -B
```

Iniciando com docker compose

```
docker-compose up
```


## Iniciando sem Docker

Clonar o repositório

```
git clone https://github.com/bmdefreitas/star-wars-api-java.git
```

Package do projeto

```
./mvnw package -B
```

*Antes de iniciar a API se faz necessário uma instância de mongo configurada. Caso tenha um mongodb em localhost, nada precisa ser configurado.*


Iniciando a API

```
java -jar target/star-wars-api-1.0.0.jar
```

## Testando

Testando a API

```
./mvnw test -B
```

## Rotas da API

```
GET    http://localhost:8080/api/v1/planetas
GET    http://localhost:8080/api/v1/planetas/1
GET    http://localhost:8080/api/v1/planetas/search?nome=Planeta
POST   http://localhost:8080/api/v1/planetas
PUT    http://localhost:8080/api/v1/planetas/1
DELETE http://localhost:8080/api/v1/planetas/1
```
