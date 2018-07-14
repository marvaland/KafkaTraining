package Kafka_Basic;


        import java.util.Properties;

        import org.apache.kafka.streams.kstream.KStreamBuilder;
        import org.apache.kafka.common.serialization.Serde;
        import org.apache.kafka.common.serialization.Serdes;
        import org.apache.kafka.streams.KafkaStreams;
        import org.apache.kafka.streams.KeyValue;
        import org.apache.kafka.streams.StreamsConfig;
        import org.apache.kafka.streams.kstream.KStream;

        import java.util.Arrays;
        import java.util.Properties;

public class Local_stream
{
    public static void main( String[] args ) {
        Properties streamsConfig = new Properties();






        // The name must be unique on the Kafka cluster
        streamsConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams");
        streamsConfig.put(StreamsConfig.STATE_DIR_CONFIG, "MyFirstApp");
        // Brokers
        streamsConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // Zookeeper
        //streamsConfig.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, args[1]);
        // SerDes for key and values
        streamsConfig.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfig.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // Serdes for the word and count
        Serde<String> stringSerde = Serdes.String();
        Serde<Long> longSerde = Serdes.Long();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> sentences = builder.stream(stringSerde, stringSerde, "test");
        // KStream<String, Long> wordCounts = sentences
        //       .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
        //     .map((key, word) -> new KeyValue<>(word, word))
        //.countByKey("Counts")
        //.toStream();
        //  wordCounts.to(stringSerde, longSerde, "javainuse");
        sentences.print();

        KafkaStreams streams = new KafkaStreams(builder, streamsConfig);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
