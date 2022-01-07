package rabbitmq.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 7390291829112611412L;

    private String userName;
    private String password;
}
