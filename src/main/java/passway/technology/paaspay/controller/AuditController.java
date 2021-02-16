package passway.technology.paaspay.controller;

import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import passway.technology.paaspay.model.WaterconsumerDetail;

import java.util.List;

import org.javers.core.Changes;
import org.javers.core.diff.Change;

@CrossOrigin(origins = "http://watervendors.passwaytechnology.co.ke", maxAge = 3600)
@RestController
@RequestMapping(value = "/audit")
public class AuditController {
	
	   private final Javers javers;
	    public AuditController(Javers javers) {
	        this.javers = javers;
	    }
//
	    @GetMapping("/waterconsumer")
		//@PreAuthorize("hasRole('ROLE_USER')")
	    public ResponseEntity<String> getWaterConsumerChanges() {
	        QueryBuilder jqlQuery = QueryBuilder.byClass(WaterconsumerDetail.class);

	        List<Change> changes = javers.findChanges(jqlQuery.build());

	        return ResponseEntity.ok().body(javers.getJsonConverter().toJson(changes));
	    }
//	    
	    @GetMapping("/waterconsumers")
		//@PreAuthorize("hasRole('ROLE_USER')")
	    public String getEmployeeChanges2() {
	        QueryBuilder jqlQuery = QueryBuilder.byClass(WaterconsumerDetail.class)
	                .withNewObjectChanges();

	        Changes changes = javers.findChanges(jqlQuery.build());

	        return "<pre>" + changes.prettyPrint() + "</pre>";
	        
//	    	 QueryBuilder jqlQuery = QueryBuilder.byClass(WaterconsumerDetail.class);
//	    	    List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());
//	    	    return javers.getJsonConverter().toJson(snapshots);
	    }
//	    
	    
}
