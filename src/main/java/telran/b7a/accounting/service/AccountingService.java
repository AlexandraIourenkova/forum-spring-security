package telran.b7a.accounting.service;

import telran.b7a.accounting.dto.RegisterDto;
import telran.b7a.accounting.dto.ResponseRoleDto;
import telran.b7a.accounting.dto.ResponseUserDto;
import telran.b7a.accounting.dto.UpdateUserDto;

public interface AccountingService {
	
	ResponseUserDto registerUser(RegisterDto registerUserDto);

	ResponseUserDto login(String user);

	ResponseUserDto deleteUser(String user);

	ResponseUserDto updateUser(String user, UpdateUserDto updateUserDto);

	ResponseRoleDto addRole(String user, String role);

	ResponseRoleDto deleteRole(String user, String role);

	void changePassword(String name, String password);

}
