package io.github.bhuwanupadhyay.order.interfaces.events;

import io.github.bhuwanupadhyay.order.application.internal.commandservices.NotifyPaymentCommandService;
import io.github.bhuwanupadhyay.order.domain.commands.NotifyPaymentCommand;
import io.github.bhuwanupadhyay.order.infrastructure.brokers.rabbitmq.OrderEventSource;
import io.github.bhuwanupadhyay.schemas.PaymentReceivedV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableBinding(OrderEventSource.class) // Bind to the channel connection for the message
public class OrderEventHandler {

  private final NotifyPaymentCommandService notifyPaymentCommandService;

  // Listen to the stream of messages on the destination
  @StreamListener(target = OrderEventSource.PAYMENT_RECEIVED_CHANNEL)
  public void receiveEvent(PaymentReceivedV1 paymentReceived) {
    LOG.info("Receive event [PaymentReceivedV1].");
    LOG.debug("Event payload {}.", paymentReceived);
    final NotifyPaymentCommand notifyPaymentCommand = new NotifyPaymentCommand();
    notifyPaymentCommand.setOrderId(paymentReceived.getOrderId().toString());
    notifyPaymentCommand.setPaymentId(paymentReceived.getPaymentId().toString());
    notifyPaymentCommandService.notifyPayment(notifyPaymentCommand);
    LOG.info("Successfully processed event [PaymentReceivedV1].");
  }
}
