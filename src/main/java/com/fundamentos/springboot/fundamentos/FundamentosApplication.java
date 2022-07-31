package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependencyImplement;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {
	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependencyImplement myBeanWithDependencyImplement;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;
	public FundamentosApplication(
			@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
			MyBean myBean,
			MyBeanWithDependencyImplement myBeanWithDependencyImplement,
			MyBeanWithProperties myBeanWithProperties,
			UserPojo userPojo,
			UserRepository userRepository,
			UserService userService
	){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependencyImplement = myBeanWithDependencyImplement;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}
	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		ejemplosAnteriores();
		saveUsersInDB();
		getInformationJpqlFromUser();
		saveWithErrorTransaction();
	}

	private void getInformationJpqlFromUser(){
//		LOGGER.info("El usuario con el correo es: " +
//				userRepository.findByUserEmail("do@do.com").orElseThrow(
//						()-> new RuntimeException("No se encontro el usuario")
//				)
//		);
//
//		userRepository.findAndSort("J", Sort.by("id").ascending())
//				.stream()
//				.forEach(user -> LOGGER.info("Usuario con metodo sort" + user));
//
//		userRepository.findByName("John")
//				.stream()
//				.forEach(user -> LOGGER.info("Usuario con query method" + user));
//
//		LOGGER.info("Usuario con query method findbyemailandname" + userRepository.findByEmailAndName("do@do.com", "John")
//				.orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
//
//		userRepository.findByNameLike("%J%")
//				.stream()
//				.forEach(user -> LOGGER.info("Usuario findbynamelike" + user));
//
//		userRepository.findByNameOrEmail(null, "do@do.com")
//				.stream()
//				.forEach(user-> LOGGER.info("User findbynameoremail" + user));

		userRepository.findByBirthDateBetween(LocalDate.of(2021,2,1),
				LocalDate.of(2021,4,30))
				.stream()
				.forEach(user -> LOGGER.info("Usuario con intervalo de fechas"+ user));

		userRepository.findByNameContainingOrderByIdDesc("%J%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario encontrado like y ordenado" + user))
		;

		LOGGER.info("El usuario a partir del named parameter es: " + userRepository.getAllByBirthDateAndEmail(
						LocalDate.of(2021, 03, 20),
						"do@do.com")
				.orElseThrow(()-> new RuntimeException("No se encontro el usuario a partir del named parametro"))
		);
	}

	private void saveUsersInDB(){
		User user1 = new User("John", "do@do.com", LocalDate.of(2021, 03, 20));
		User user2 = new User("Julia", "julia@do.com", LocalDate.of(2025, 04, 2));
		User user3 = new User("Daniela", "Daniela@do.com", LocalDate.of(2021, 02, 25));
		User user4 = new User("Ramon", "rr@do.com", LocalDate.of(2019, 10, 13));
		List<User> list = Arrays.asList(user1,user2,user3,user4);
		list.stream().forEach(userRepository::save);

	}
	private void saveWithErrorTransaction(){
		User test1 = new User("TestTrans1", "TestTrans1@do.com", LocalDate.now());
		User test2 = new User("TestTrans2", "TestTrans2@do.com", LocalDate.now());
		User test3 = new User("TestTrans3", "TestTrans1@do.com", LocalDate.now());
		User test4 = new User("TestTrans4", "TestTrans4@do.com", LocalDate.now());

		List<User> users = Arrays.asList(test1,test2,test3,test4);

		try {
			userService.saveTransactional(users);
		}catch (Exception e){
			LOGGER.error("Esta es una excepción dentro del met transaccional" + e);
		}

		userService.getAllUsers()
				.stream()
				.forEach(user -> LOGGER.info("Este es el find user all" + user));

	}
	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependencyImplement.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword() + "edad -> " + userPojo.getAge());
		try{
			int value = 10/0;
			LOGGER.info("Mi valor:" + value);
		}catch (Exception e){
			LOGGER.error("Esto es un error al dividir por 0" + e.getMessage());
		}
	}
}
