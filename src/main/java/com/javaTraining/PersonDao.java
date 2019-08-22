package com.javaTraining;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonDao {

    private JdbcTemplate jdbcTemplate;

    public PersonDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveOrUpdate(Person person) {
        if (person.getId() > 0) {
            // update
            String sql = "UPDATE contact SET name=?, email=?, address=?, "
                    + "telephone=? WHERE contact_id=?";
            jdbcTemplate.update(sql, person.getName(), person.getEmail(),
                    person.getAddress(), person.getTelephone(), person.getId());
        } else {
            // insert
            String sql = "INSERT INTO contact (name, email, address, telephone)"
                    + " VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, person.getName(), person.getEmail(),
                    person.getAddress(), person.getTelephone());
        }
    }

    public void delete(int personId) {
        String sql = "DELETE FROM contact WHERE contact_id=?";
        jdbcTemplate.update(sql, personId);
    }


    public Person get(int personId) {
        String sql = "SELECT * FROM contact WHERE contact_id=" + personId;
        return jdbcTemplate.query(sql, new ResultSetExtractor<Person>() {

            public Person extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    Person person = new Person();
                    person.setId(rs.getInt("contact_id"));
                    person.setName(rs.getString("name"));
                    person.setEmail(rs.getString("email"));
                    person.setAddress(rs.getString("address"));
                    person.setTelephone(rs.getString("telephone"));
                    return person;
                }
                return null;
            }

        });
    }

    public List<Person> list() {
        String sql = "SELECT * FROM person";
        List<Person> listPerson = jdbcTemplate.query(sql, new RowMapper<Person>() {

            public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person aPerson = new Person();

                aPerson.setId(rs.getInt("person_id"));
                aPerson.setName(rs.getString("name"));
                aPerson.setEmail(rs.getString("email"));
                aPerson.setAddress(rs.getString("address"));
                aPerson.setTelephone(rs.getString("telephone"));

                return aPerson;
            }

        });

        return listPerson;
    }
}