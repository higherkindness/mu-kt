package higherkindness.mu.meta.rpc.protocol

sealed class StreamingType {
  class RequestStreaming : StreamingType()
  class ResponseStreaming : StreamingType()
  class BidirectionalStreaming : StreamingType()
}

sealed class SerializationType {
  class Protobuf : SerializationType()
  class Avro : SerializationType()
  class AvroWithSchema : SerializationType()
}

sealed class CompressionType {
  class Identity : CompressionType()
  class Gzip : CompressionType()
}

// TODO: Add @message annotation
object Empty