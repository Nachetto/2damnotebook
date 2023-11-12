package model.errores;

import jakarta.ws.rs.core.Response;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {

  public CustomException(String message,Response.Status codigo) {
    super(message);
    this.codigo = codigo;
  }

  private final Response.Status codigo;

}
