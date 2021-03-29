#### Server of a Laboratory-item-store

#### Maven, JDK 11, Docker are necessary to test, build, run. We use Lombok. To open in IntelliJ please install Lombok plugin.

**Local run.** Runs application locally.<br>
`./run local_app`

**Unit tests.** Runs all unit tests.<br>
`./run unit_test`

**Integration tests.** Runs all integration tests.<br>
`./run integration_test`

**Build.** Builds a docker image, which runs the application inside a container.<br>
`./run build_docker`

#### To run docker image:
`docker run --network="host" laboratory-item-store:latest-snapshot`<br>

Server listens to port 8080