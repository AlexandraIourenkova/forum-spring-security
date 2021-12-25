package telran.b7a.accounting.service;


import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.b7a.accounting.dao.UserAccountingRepository;
import telran.b7a.accounting.dto.RegisterDto;
import telran.b7a.accounting.dto.ResponseRoleDto;
import telran.b7a.accounting.dto.ResponseUserDto;
import telran.b7a.accounting.dto.UpdateUserDto;
import telran.b7a.accounting.exeption.UserExistsException;
import telran.b7a.accounting.exeption.UserNotFoundException;
import telran.b7a.accounting.model.UserAccount;

@Service
public class AccountingServiceImpl implements AccountingService {
	
	UserAccountingRepository repository;
	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;
	

	@Autowired
	public AccountingServiceImpl(UserAccountingRepository repository, ModelMapper modelMapper, PasswordEncoder passwardEncoder) {
		this.repository = repository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwardEncoder;
	}

	@Override
	public ResponseUserDto registerUser(RegisterDto registerUserDto) {
		if (repository.existsById(registerUserDto.getLogin())) {
			throw new UserExistsException(registerUserDto.getLogin());
		}
		UserAccount userAccount = modelMapper.map(registerUserDto, UserAccount.class);
		userAccount.addRole("USER".toUpperCase());
		String password = passwordEncoder.encode(registerUserDto.getPassword());
		userAccount.setPassword(password );
		repository.save(userAccount);
		return modelMapper.map(userAccount, ResponseUserDto.class);
	}

	@Override
	public ResponseUserDto login(String login) {
		UserAccount userAccount = repository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		return modelMapper.map(userAccount, ResponseUserDto.class);
	}

	@Override
	public ResponseUserDto deleteUser(String user) {
		UserAccount userAccount = repository.findById(user)
				.orElseThrow(() -> new UserNotFoundException(user));
		repository.delete(userAccount);
		return modelMapper.map(userAccount, ResponseUserDto.class);
	}

	@Override
	public ResponseUserDto updateUser(String user, UpdateUserDto updateUserDto) {
		UserAccount userAccount = repository.findById(user)
				.orElseThrow(() -> new UserNotFoundException(user));
		if(updateUserDto.getFirstName() != null) {
			userAccount.setFirstName(updateUserDto.getFirstName());
		}
		if(updateUserDto.getLastName() != null) {
			userAccount.setLastName(updateUserDto.getLastName());
		}
		repository.save(userAccount);
		return modelMapper.map(userAccount, ResponseUserDto.class); 
	}

	@Override
	public ResponseRoleDto addRole(String user, String role) {
		UserAccount userAccount = repository.findById(user)
				.orElseThrow(() -> new UserNotFoundException(user));
		userAccount.addRole(role.toUpperCase());
		repository.save(userAccount);
		return modelMapper.map(userAccount, ResponseRoleDto.class); 
		
	}

	@Override
	public ResponseRoleDto deleteRole(String user, String role) {
		UserAccount userAccount = repository.findById(user)
				.orElseThrow(() -> new UserNotFoundException(user));
		userAccount.removeRole(role.toUpperCase());
		repository.save(userAccount);
		return modelMapper.map(userAccount, ResponseRoleDto.class);
	}

	@Override
	public void changePassword(String user, String password) {
		UserAccount userAccount = repository.findById(user).orElseThrow(() -> new UserNotFoundException());
			if(password != null) {
			userAccount.setPassword(passwordEncoder.encode(password));
			userAccount.setPasswordChangeDate(LocalDate.now());
			userAccount.setPasswordExpDate(LocalDate.now().plusDays(60));
			repository.save(userAccount);
			}
		}
}
