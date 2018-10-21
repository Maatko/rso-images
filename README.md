RSO:
----
	
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
	docker run -d --name pg-images -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=image -p 5433:5432 postgres:10.5	
		
	docker run hello-world # creates and runs docker container from hello-world image
	
	dockerfile:
		FROM <docker image>  # osnovna slika nad katero gradimo
		RUN mdkir <dir>  # zažene ukaz nad obstoječo sliko
		WORKDIR <dir>  # nastavi delovni direktorij
		ADD <image>  # kopira nove datoteke, direktorije v datotečni sistem vsebnika (generira .jar)
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
	Dependency Injection - zagotavlja, da so viri potrebni za izvajanje komponente na voljo @Resource