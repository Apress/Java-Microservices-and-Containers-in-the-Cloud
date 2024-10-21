package se.callista.blog.synch_kafka.request_reply_util;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.GenericMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.lang.Nullable;

/**
 * Specialization of the ReplyingKafkaTemplate to manage the Return Address pattern,
 * i.e. setting the REPLY_TOPIC and REPLY_PARTITION based on the configured reply topic.
 * 
 * Also complete the API to resemble the KafkaTemplate (overloading the sendAndReceive() 
 * method with simplified parameters, and adding corresponding overloaded methods which
 * use a configured default topic):
 */
public class PartitionAwareReplyingKafkaTemplate<K, V, R> extends ReplyingKafkaTemplate<K, V, R> implements PartitionAwareReplyingKafkaOperations<K, V, R> {
    

  public PartitionAwareReplyingKafkaTemplate(ProducerFactory<K, V> producerFactory,
      GenericMessageListenerContainer<K, R> replyContainer) {
    super(producerFactory, replyContainer);
  }

  private TopicPartition getFirstAssignedReplyTopicPartition() {
    if (getAssignedReplyTopicPartitions() != null &&
        getAssignedReplyTopicPartitions().iterator().hasNext()) {
      TopicPartition replyPartition = getAssignedReplyTopicPartitions().iterator().next();
      return replyPartition;
    } else {
      throw new KafkaException("Illegal state: No reply partition is assigned to this instance");
    }
  }

  private static byte[] intToBytesBigEndian(final int data) {
    return new byte[] {(byte) ((data >> 24) & 0xff), (byte) ((data >> 16) & 0xff),
        (byte) ((data >> 8) & 0xff), (byte) ((data >> 0) & 0xff),};
  }

  @Override
  public RequestReplyFuture<K, V, R> sendAndReceiveDefault(@Nullable V data) {
    return sendAndReceive(getDefaultTopic(), data);
  }

  @Override
  public RequestReplyFuture<K, V, R> sendAndReceiveDefault(K key, @Nullable V data) {
    return sendAndReceive(getDefaultTopic(), key, data);
  }

  @Override
  public RequestReplyFuture<K, V, R> sendAndReceiveDefault(Integer partition, K key, V value) {
    return sendAndReceive(getDefaultTopic(), partition, key, value);
  }

  @Override
  public RequestReplyFuture<K, V, R> sendAndReceiveDefault(Integer partition, Long timestamp, K key, V value) {
    return sendAndReceive(getDefaultTopic(), partition, timestamp, key, value);
  }

  @Override
  public RequestReplyFuture<K, V, R> sendAndReceive(String topic, @Nullable V data) {
    ProducerRecord<K, V> record = new ProducerRecord<>(topic, data);
    return doSendAndReceive(record);
  }

  @Override
  public RequestReplyFuture<K, V, R> sendAndReceive(String topic, K key, @Nullable V data) {
    ProducerRecord<K, V> record = new ProducerRecord<>(topic, key, data);
    return doSendAndReceive(record);
  }

  @Override
  public RequestReplyFuture<K, V, R> sendAndReceive(String topic, Integer partition, K key, V data) {
    ProducerRecord<K, V> record = new ProducerRecord<>(topic, partition, key, data);
    return doSendAndReceive(record);
  }

  @Override
  public RequestReplyFuture<K, V, R> sendAndReceive(String topic, Integer partition, Long timestamp, K key, V data) {
    ProducerRecord<K, V> record = new ProducerRecord<>(topic, partition, timestamp, key, data);
    return doSendAndReceive(record);
  }

  @Override
  public RequestReplyFuture<K, V, R> sendAndReceive(ProducerRecord<K, V> record) {
    return doSendAndReceive(record);
  }
  
    protected RequestReplyFuture<K, V, R> doSendAndReceive(ProducerRecord<K, V> record) {

        TopicPartition replyTopicPartition = getFirstAssignedReplyTopicPartition();
        String requestPartition = record.partition() != null ? record.partition().toString() : "null";
        String requestKey = record.key() != null ? record.key().toString() : "null";
        int replyPartition = replyTopicPartition.partition();
        this.logger.debug("Using Request partition : " + requestPartition);
        this.logger.debug("Using Request key : " + requestKey);
        this.logger.debug("Using Reply partition : " + replyPartition);
        record.headers()
            .add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopicPartition.topic().getBytes()))
            .add(new RecordHeader(KafkaHeaders.REPLY_PARTITION, intToBytesBigEndian(replyPartition)));
        return super.sendAndReceive(record);
    }

}
