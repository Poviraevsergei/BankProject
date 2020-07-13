package by.park.dao;

import by.park.domain.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();

    Role findById(Long roleId);

    Role save(Role role);

    Role update(Role role);

    Role delete(Long roleId);
}
