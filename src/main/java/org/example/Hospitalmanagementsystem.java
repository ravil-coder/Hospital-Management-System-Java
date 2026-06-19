package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospitalmanagementsystem {

    private static final String url = "jdbc:mysql://localhost:3307/hospital";
    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args) {

        try {
            Connection connection =
                    DriverManager.getConnection(url, username, password);

            Scanner scanner = new Scanner(System.in);

            patients patient = new patients(connection, scanner);
            doctor doctor = new doctor(connection);
            appointment appointment = new appointment(connection, scanner);

            while (true) {

                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:
                        patient.addPatient();
                        break;

                    case 2:
                        patient.viewPatients();
                        break;

                    case 3:
                        doctor.viewDoctors();
                        break;

                    case 4:
                        appointment.bookAppointment(patient, doctor);
                        break;

                    case 5:
                        System.out.println("Thank You!");
                        connection.close();
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}