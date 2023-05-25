import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ClinicImpl clinic = new ClinicImpl();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Load data from file
        clinic.loadDataFromFile("clinic.dat");

        // Create and instantiate objects
        Patient patient = new Patient("John Doe", "123-456-7890", "123 Main St", 1, "Allergies");
        Doctor doctor = new Doctor("Jane Smith", "456-789-0123", "456 Oak St", 1, "Cardiology");
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);
        Appointment appointment = new Appointment(patient, doctor, appointmentTime);

        // Add objects to clinic
        clinic.addPatient(patient);
        clinic.addDoctor(doctor);
        clinic.scheduleAppointment(patient, doctor, appointmentTime);

        // Read information from user
        System.out.print("Enter patient name to search: ");
        String patientName = scanner.nextLine();
        List<Patient> patients = clinic.searchPatientsByName(patientName);
        for (Patient p : patients) {
            System.out.println(p.getName() + " - " + p.getMedicalHistory());
        }

        // Modify objects
        patient.setAddress("321 Main St");
        appointment.setAppointmentTime(appointmentTime.plusHours(1));

        // Perform special tasks
        printAppointmentsByDoctor(doctor);
        clinic.saveDataToFile("clinic.dat");
    }

    public static void printAppointmentsByDoctor(Doctor doctor) {
        List<Appointment> appointments = clinic.getAppointmentsByDoctor(doctor);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        System.out.println("Appointments for " + doctor.getName() + ":");
        for (Appointment appointment : appointments) {
            System.out.println(appointment.getPatient().getName() + " - " + appointment.getAppointmentTime().format(formatter));
        }
    }
}
