RSO:
----

BASICS:

    mvn clean package
	docker build -t rokzidarn/rso-images:0.1 .
	docker push rokzidarn/rso-images:0.1
	docker run -d rokzidarn/rso-images:0.1
	
PROJECT:

	IntelliJ configuration:
		Main class: EeApplication -> com.kumuluz.ee.EeApplication
		Working directory: <project name>
		Classpath module <project-name>-api
	
	api dir:
		<entity>Application.java - defines application path (version /v1)
		<entity>Resource.java - REST CRUD operations, [@Path, @GET], (business logic calls + responses)
	models dir:
		init-<entity>.sql - database seeder
		<entity>.java - ORM entity class, [@Entity, @NamedQueries, @Id, @Column], get/set methods
	services dir:
		<entity>Bean.java - needs entity manager (@Inject from PersistenceManager.java), performs transactions
		on database (begin(), commit(), rollback()), business logic, receives calls from api dir
		

MVN:
	
	mvn clean package -> builds all modules, creates jar -> \api\target\	
	
	mvn package - prevajanje in pakiranje
	mvn install - namestitev v lokalni repozitorij
	mvn clean - čiščenje rezultatov prejšnjih buildov
	mvn site - generiranje spletne strani
	mvn deploy - namestitev v skupni Maven repozitorij
	
	izdelava projekta:
		mvn archetype:generate 
		-DgroupId = com.mycompany.app 
		-DartifactId = my-app 
		-DarchetypeArtifactId = maven-archetype-quickstart 
		-DinteractiveMode = false
	
DOCKER:

    docker rm -v pg-images
	docker run -d --name pg-images -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=image -p 5432:5432 postgres:10.5	
		
	docker run hello-world # creates and runs docker container from hello-world image
	
	dockerfile:
		FROM <docker image>  # osnovna slika nad katero gradimo
		RUN mdkir <dir>  # zažene ukaz nad obstoječo sliko
		WORKDIR <dir>  # nastavi delovni direktorij
		ADD <image>  # kopira nove datoteke, direktorije v datotečni sistem vsebnika, v sliko
		EXPOSE <port number>  # na katerih portih posluša vsebnik
		CMD <image>  # privzete nastavitve izvajajočega vsebnika
		
	config.yaml:
		datasource connection url = localhost
		
POSTMAN:
	 
	 http://localhost:<exposed port number>/<version>/<endpoint>
	
JAVAEE:
	
	JAX-RS - Java API REST Services
		@Path, @GET, @POST
	JTA - Java Transaction API
		@RequiresNew
	JPQL - Java + SQL
	JPA - Java Persistence API (ORM)
		persist(), find(), merge(), remove(), createQuery(), createNamedQuery()		
	EJB - Entity Java Beans - business logic
	
MICROSERVICES:

    4 main services: image uploader (3 services: [caption, user, binary file (image) -> request -> S3 image service -> URL, caption, user -> DB], 
        image catalog, image editing (delete current, reupload new image), image comments
    2 support services: messaging service
    
TRAVIS:

    1. mvn clean package
    2. Dockerfile configuration
    3. docker build -t rokzidarn/rso-images:0.1 .
    4. docker push rokzidarn/rso-images:0.1
    
DOCKER CONFIG:

    environment variables in docker: docker run -e ENVIRONMENT_VAR=value
    docker-compose.yml
    
    docker network create rso  # creates network between DB and my service
    docker run -d --name pg-images --network rso -e \
        POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=image -p 5432:5432 postgres:10.5
    docker run -d --name rso-images --network rso -e \
        KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-images:5432/image -p 8081:8081 rokzidarn/rso-images:0.1
        # environemnt variable from config.yaml structure
        # config.yaml: localhost -> pg-images
        
    
        
SERVICE DISCOVERY:
    
    docker run -d -p 2379:2379 \
                 --name etcd \
                 quay.io/coreos/etcd:latest \
                 usr/local/bin/etcd \
                 --name my-etcd-1 \
                 --data-dir /etcd-data \
                 --listen-client-urls http://0.0.0.0:2379 \
                 --advertise-client-urls http://0.0.0.0:2379 \
                 --listen-peer-urls http://0.0.0.0:2380 \
                 --initial-advertise-peer-urls http://0.0.0.0:2380 \
                 --initial-cluster my-etcd-1=http://0.0.0.0:2380 \
                 --initial-cluster-token my-etcd-token \
                 --initial-cluster-state new \
                 --auto-compaction-retention 1 \
                 -cors="*"
                 
    http://henszey.github.io/etcd-browser/
    http://localhost:2379
    
    docker run -d --name pg-images --network rso -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=image \
        -p 5432:5432 postgres:10.5
    docker run -d --name rso-images --network rso -p 8081:8081 rokzidarn/rso-images:0.1
    