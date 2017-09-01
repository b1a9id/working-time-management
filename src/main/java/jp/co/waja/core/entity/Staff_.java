package jp.co.waja.core.entity;

import javax.persistence.metamodel.*;
import java.time.LocalDate;

@StaticMetamodel(Staff.class)
public class Staff_ {

	public static volatile SingularAttribute<Staff, Long> id;
	public static volatile SingularAttribute<Staff, Team> team;
	public static volatile SingularAttribute<Staff, String> nameLast;
	public static volatile SingularAttribute<Staff, String> nameFirst;
	public static volatile SingularAttribute<Staff, String> nameLastKana;
	public static volatile SingularAttribute<Staff, String> nameFirstKana;
	public static volatile SingularAttribute<Staff, String> email;
	public static volatile SingularAttribute<Staff, Staff.Gender> gender;
	public static volatile SingularAttribute<Staff, Staff.EmploymentType> employmentType;
	public static volatile SingularAttribute<Staff, LocalDate> enteredDate;
	public static volatile SingularAttribute<Staff, Boolean> telework;
	public static volatile SingularAttribute<Staff, Boolean> disabled;
}
