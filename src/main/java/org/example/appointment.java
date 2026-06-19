package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class appointment {

    private Connection connection;
    private Scanner scanner;

    public appointment(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // CHECK DOCTOR AVAILABILITY
    public boolean checkDoctorAvailability(int doctorId, String appointmentDate) {

        String query =
                "SELECT * FROM appointment WHERE doctor_id = ? AND appointment_date = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    // BOOK APPOINTMENT
    public void bookAppointment(patients patient, doctor doctor) {

        System.out.print("Enter Patient Id: ");
        int patientId = scanner.nextInt();

        System.out.print("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = scanner.nextLine();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {

            if (checkDoctorAvailability(doctorId, appointmentDate)) {

                String query = "INSERT INTO appointment(patients_id, doctor_id, appointment_date) VALUES (?, ?, ?)";

                try {

                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {

                        System.out.println("Appointment Booked Successfully!"
                        );

                    } else {

                        System.out.println("Failed to Book Appointment!"
                        );
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {

                System.out.println("Doctor not available on this date!"
                );
            }

        } else {

            System.out.println("Patient or Doctor does not exist!"
            );
        }
    }
}