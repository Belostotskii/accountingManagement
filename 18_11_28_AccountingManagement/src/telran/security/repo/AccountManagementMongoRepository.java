package telran.security.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import telran.security.accounting.mongo.Account;

@Repository
public interface AccountManagementMongoRepository extends MongoRepository<Account, String>{
	
	

}
