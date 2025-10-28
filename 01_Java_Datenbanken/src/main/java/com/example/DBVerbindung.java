package com.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBVerbindung {

  private Connection con = null;
  private String dbAdresse = "localhost";
  private String dbInstanz = "zoo";

  public void verbinden(String nutzer, String passwort) {
    try {
      con =
        DriverManager.getConnection(
          "jdbc:postgresql://" + dbAdresse + "/" + dbInstanz,
          nutzer,
          passwort
        );
    } catch (SQLException e) {
      ausnahmeAusgeben(e);
    }
  }

  private void ausnahmeAusgeben(SQLException e) {
    while (e != null) {
      System.err.println("Fehlercode: " + e.getErrorCode());
      System.err.println("SQL State: " + e.getSQLState());
      System.err.println(e);
      e = e.getNextException();
    }
  }

  public void verbindungTrennen() {
    if (con == null) {
      System.out.println("keine Verbindung vorhanden");
      return;
    }

    try {
      con.close();
    } catch (SQLException e) {
      ausnahmeAusgeben(e);
    }
  }

  public void verbindungAnalysieren() {
    if (con == null) {
      System.out.println("keine Verbindung vorhanden");
      return;
    }

    try {
      DatabaseMetaData dbmd = con.getMetaData();
      System.out.println(
        "DB-Name: " +
        dbmd.getDatabaseProductName() +
        "\nDB-Version: " +
        dbmd.getDatabaseMajorVersion() +
        "\nDB-Release: " +
        dbmd.getDriverMinorVersion() +
        "\nTransaktionen erlaubt: " +
        dbmd.supportsTransactions() +
        "\nbeachtet GroßKlein: " +
        dbmd.storesMixedCaseIdentifiers() +
        "\nunterstützt UNION: " +
        dbmd.supportsUnion() +
        "\nmax. Prozedurname: " +
        dbmd.getMaxProcedureNameLength()
      );
    } catch (SQLException e) {
      ausnahmeAusgeben(e);
    }
  }

  public static void main(String[] s) {
    DBVerbindung db = new DBVerbindung();
    db.verbinden("postgres", "geheim");
    db.verbindungAnalysieren();
    db.verbindungTrennen();
  }
}
