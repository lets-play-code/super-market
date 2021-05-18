docker run -d -v ~/dev/data/postgresql:/var/lib/postgresql/data -e PGDATA=/var/lib/postgresql/data/pgdata -e POSTGRES_PASSWORD=supermarket --name supermarket -p 5432:5432 postgres:9.6-alpine
