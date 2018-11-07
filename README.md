RSO:
----

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
    
    move to dir with docker-compose.yml
    docker-compose up -d 
    
    docker logs <service-image>
        ETCD2Configuration -- Using namespace: 
            environments/dev/services/rso-images/1.0.0/config/app-properties/external-services/enabled
            
    http://henszey.github.io/etcd-browser/
        http://localhost:2379