package nus.team1.adproject.ppmtool.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nus.team1.adproject.ppmtool.domain.Projectmember;

@Repository
public interface ProjectMemberRepository extends CrudRepository<Projectmember, Long> {

	List<Projectmember> findProjectMemberByuserName(String userName);

	List<Projectmember> findProjectMemberByProjectIdentifier(String projectIdentifier);

	@Query("SELECT pm FROM Projectmember pm WHERE pm.projectIdentifier=:pi AND pm.userName=:un")
	Projectmember findProjectmemberByPIdentifierUName(@Param("pi") String projectIdentifier,
			@Param("un") String userName);
}