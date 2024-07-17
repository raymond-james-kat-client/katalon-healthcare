package katalon.model

public class DoctorModel {
	String email;
	String fullName;
	String phoneNumber;
	String gender;
	String birthday;
	String degree;
	String speciality;
	String address;
	String description;
	String status

	public void setDoctor(String fullName, String birthday, String degree, String speciality, String phoneNumber, String gender, String address, String description) {
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.birthday = birthday;
		this.degree = degree;
		this.speciality = speciality;
		this.address = address;
		this.description = description;
	}

	public void setDoctor(String email, String fullName, String birthday, String degree, String speciality, String status) {
		this.email = email;
		this.fullName = fullName;
		this.birthday = birthday;
		this.degree = degree;
		this.speciality = speciality;
		this.status = status;
	}

	public boolean compare(String fullName, String birthday, String degree, String speciality, String phoneNumber, String gender, String address, String description) {
		return !(this.fullName.compareTo(fullName) & this.phoneNumber.compareTo(phoneNumber) & this.gender.compareTo(gender) &
				this.birthday.compareTo(birthday) & this.degree.compareTo(degree) & this.speciality.compareTo(speciality) &
				this.address.compareTo(address) & this.description.compareTo(description));
	}

	public boolean compare(String fullName, String birthday, String degree, String speciality) {
		return !(this.fullName.compareTo(fullName) & this.birthday.compareTo(birthday) &
				this.degree.compareTo(degree) & this.speciality.compareTo(speciality));
	}

	public boolean compare(String email, String fullName, String birthday, String degree, String speciality) {
		return !(this.fullName.compareTo(fullName) & this.birthday.compareTo(birthday) & this.email.compareTo(email) &
				this.degree.compareTo(degree) & this.speciality.compareTo(speciality));
	}
}
