# Tabi

A travel agency management system written for a class project.

## Prerequisites

- [JDK 21](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Docker](https://www.docker.com/)

## Running

Before building this project, an `.env` file has to be created in the project's root directory containing the following variables:

- `POSTGRES_DB` - The name of the database (and also the Docker container in which the Postgres will be running), e.g. `tabi-db`.
- `POSTGRES_USER` - The name of the database user, e.g. `tabi`.
- `POSTGRES_PASSWORD` - The password for the database user, e.g. `P@ssw0rd!`.
- `POSTGRES_HOSTNAME` - The hostname that Postgres will be listening on, e.g. `localhost`.
- `POSTGRES_PORT` - The port that Postgres will be listening on, e.g. `5432`.
- `APP_PROFILE` - A variable that is set to `prod` when the build is meant to be deployed. Otherwise, it is set to `local`. 
- `STRIPE_PUBLIC_KEY` - A public key for the Stripe API. Should start with `pk`.
- `STRIPE_PRIVATE_KEY` - A private key for the Stripe API. Should start with `sk`.

The Stripe public and private keys can be obtained by creating a free account at <https://stripe.com/>.

There are two ways of running this project.

### Option 1

```console
docker compose up
```

Sometimes, the `docker` and `compose` parts need to be separated by `-` rather than space, depending on the platform.
Note that, in order of any code changes to be reflected in the build, the old Docker image needs to be removed.
Because of this, it is not suitable for developement.

### Option 2

```console
docker compose up database
```

Next, from another terminal window, enter the following:

```console
mvn spring-boot:run
```

## Usage

In order to test the API, go to the following address: <http://localhost:8080/swagger-ui/index.html#/>.
There is no frontend.

## License

[MIT](https://github.com/m-strzelec/Tabi/blob/main/LICENSE)
