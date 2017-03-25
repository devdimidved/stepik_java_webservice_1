package dbService.dataSets;

public class UsersDataSet {
    private long id;
    private String name;
    private String password;

    public UsersDataSet(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UsersDataSet(String name, String password) {
        this.id = -1;
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
