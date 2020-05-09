package io.github.bhuwanupadhyay.order.domain.commands;

import lombok.Data;

@Data
public class CreateOrderCommand {

  private String itemId;
  private Integer quantity;
}
