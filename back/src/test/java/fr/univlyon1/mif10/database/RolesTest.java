package fr.univlyon1.mif10.database;

import fr.univlyon1.mif10.dto.RoleDTO;
import fr.univlyon1.mif10.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RolesTest implements TablesTest{
    @Autowired
    private RoleRepository roleRepository;
    private RoleDTO savedRole;

    @Test
    public void allTests() {
        testSave();
        findById();
        testDelete();
    }

    @Override
    public void testSave() {
        RoleDTO role = new RoleDTO();
        role.setRoleName("USER_TEST");
        savedRole = roleRepository.save(role);
        assertThat(savedRole.getId()).isNotNull();
        assertThat(savedRole.getRoleName()).isEqualTo("USER_TEST");
    }

    @Override
    public void findById() {
        RoleDTO foundRole = roleRepository.findById(savedRole.getId()).orElse(null);
        assert foundRole != null;
        assertThat(foundRole.getRoleName()).isEqualTo("USER_TEST");
    }

    @Override
    public void testDelete() {
        int nbRoles = (int) roleRepository.count();
        roleRepository.delete(savedRole);
        assertThat(roleRepository.count()).isEqualTo(
                Math.max(nbRoles - 1, 0)
        );
    }
}
