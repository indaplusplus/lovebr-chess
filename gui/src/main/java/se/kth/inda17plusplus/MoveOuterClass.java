// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: se/kth/inda17plusplus/move.proto

package se.kth.inda17plusplus;

public final class MoveOuterClass {
  private MoveOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MoveOrBuilder extends
      // @@protoc_insertion_point(interface_extends:se.kth.inda17plusplus.Move)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * the move in Algebraic Notation, + for checks, # for checkmates, 1-0 / 0-1 / 1/2-1/2 for game end
     * </pre>
     *
     * <code>optional string move = 1;</code>
     */
    String getMove();
    /**
     * <pre>
     * the move in Algebraic Notation, + for checks, # for checkmates, 1-0 / 0-1 / 1/2-1/2 for game end
     * </pre>
     *
     * <code>optional string move = 1;</code>
     */
    com.google.protobuf.ByteString
        getMoveBytes();

    /**
     * <pre>
     * the state after the move in Forsyth-Edwards Notation
     * </pre>
     *
     * <code>optional string resulting_state = 2;</code>
     */
    String getResultingState();
    /**
     * <pre>
     * the state after the move in Forsyth-Edwards Notation
     * </pre>
     *
     * <code>optional string resulting_state = 2;</code>
     */
    com.google.protobuf.ByteString
        getResultingStateBytes();

    /**
     * <code>optional bool last_move_errored = 3;</code>
     */
    boolean getLastMoveErrored();
  }
  /**
   * Protobuf type {@code se.kth.inda17plusplus.Move}
   */
  public  static final class Move extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:se.kth.inda17plusplus.Move)
      MoveOrBuilder {
    // Use Move.newBuilder() to construct.
    private Move(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Move() {
      move_ = "";
      resultingState_ = "";
      lastMoveErrored_ = false;
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private Move(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              String s = input.readStringRequireUtf8();

              move_ = s;
              break;
            }
            case 18: {
              String s = input.readStringRequireUtf8();

              resultingState_ = s;
              break;
            }
            case 24: {

              lastMoveErrored_ = input.readBool();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return MoveOuterClass.internal_static_se_kth_inda17plusplus_Move_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return MoveOuterClass.internal_static_se_kth_inda17plusplus_Move_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Move.class, Builder.class);
    }

    public static final int MOVE_FIELD_NUMBER = 1;
    private volatile Object move_;
    /**
     * <pre>
     * the move in Algebraic Notation, + for checks, # for checkmates, 1-0 / 0-1 / 1/2-1/2 for game end
     * </pre>
     *
     * <code>optional string move = 1;</code>
     */
    public String getMove() {
      Object ref = move_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        move_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * the move in Algebraic Notation, + for checks, # for checkmates, 1-0 / 0-1 / 1/2-1/2 for game end
     * </pre>
     *
     * <code>optional string move = 1;</code>
     */
    public com.google.protobuf.ByteString
        getMoveBytes() {
      Object ref = move_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        move_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int RESULTING_STATE_FIELD_NUMBER = 2;
    private volatile Object resultingState_;
    /**
     * <pre>
     * the state after the move in Forsyth-Edwards Notation
     * </pre>
     *
     * <code>optional string resulting_state = 2;</code>
     */
    public String getResultingState() {
      Object ref = resultingState_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        resultingState_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * the state after the move in Forsyth-Edwards Notation
     * </pre>
     *
     * <code>optional string resulting_state = 2;</code>
     */
    public com.google.protobuf.ByteString
        getResultingStateBytes() {
      Object ref = resultingState_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        resultingState_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LAST_MOVE_ERRORED_FIELD_NUMBER = 3;
    private boolean lastMoveErrored_;
    /**
     * <code>optional bool last_move_errored = 3;</code>
     */
    public boolean getLastMoveErrored() {
      return lastMoveErrored_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getMoveBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, move_);
      }
      if (!getResultingStateBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, resultingState_);
      }
      if (lastMoveErrored_ != false) {
        output.writeBool(3, lastMoveErrored_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getMoveBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, move_);
      }
      if (!getResultingStateBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, resultingState_);
      }
      if (lastMoveErrored_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(3, lastMoveErrored_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Move)) {
        return super.equals(obj);
      }
      Move other = (Move) obj;

      boolean result = true;
      result = result && getMove()
          .equals(other.getMove());
      result = result && getResultingState()
          .equals(other.getResultingState());
      result = result && (getLastMoveErrored()
          == other.getLastMoveErrored());
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = (37 * hash) + MOVE_FIELD_NUMBER;
      hash = (53 * hash) + getMove().hashCode();
      hash = (37 * hash) + RESULTING_STATE_FIELD_NUMBER;
      hash = (53 * hash) + getResultingState().hashCode();
      hash = (37 * hash) + LAST_MOVE_ERRORED_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getLastMoveErrored());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Move parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Move parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Move parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Move parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Move parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Move parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Move parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Move parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Move parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Move parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Move prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code se.kth.inda17plusplus.Move}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:se.kth.inda17plusplus.Move)
        MoveOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return MoveOuterClass.internal_static_se_kth_inda17plusplus_Move_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return MoveOuterClass.internal_static_se_kth_inda17plusplus_Move_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Move.class, Builder.class);
      }

      // Construct using se.kth.inda17plusplus.MoveOuterClass.Move.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        move_ = "";

        resultingState_ = "";

        lastMoveErrored_ = false;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return MoveOuterClass.internal_static_se_kth_inda17plusplus_Move_descriptor;
      }

      public Move getDefaultInstanceForType() {
        return Move.getDefaultInstance();
      }

      public Move build() {
        Move result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public Move buildPartial() {
        Move result = new Move(this);
        result.move_ = move_;
        result.resultingState_ = resultingState_;
        result.lastMoveErrored_ = lastMoveErrored_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Move) {
          return mergeFrom((Move)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Move other) {
        if (other == Move.getDefaultInstance()) return this;
        if (!other.getMove().isEmpty()) {
          move_ = other.move_;
          onChanged();
        }
        if (!other.getResultingState().isEmpty()) {
          resultingState_ = other.resultingState_;
          onChanged();
        }
        if (other.getLastMoveErrored() != false) {
          setLastMoveErrored(other.getLastMoveErrored());
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Move parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Move) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private Object move_ = "";
      /**
       * <pre>
       * the move in Algebraic Notation, + for checks, # for checkmates, 1-0 / 0-1 / 1/2-1/2 for game end
       * </pre>
       *
       * <code>optional string move = 1;</code>
       */
      public String getMove() {
        Object ref = move_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          move_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       * the move in Algebraic Notation, + for checks, # for checkmates, 1-0 / 0-1 / 1/2-1/2 for game end
       * </pre>
       *
       * <code>optional string move = 1;</code>
       */
      public com.google.protobuf.ByteString
          getMoveBytes() {
        Object ref = move_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          move_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * the move in Algebraic Notation, + for checks, # for checkmates, 1-0 / 0-1 / 1/2-1/2 for game end
       * </pre>
       *
       * <code>optional string move = 1;</code>
       */
      public Builder setMove(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        move_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * the move in Algebraic Notation, + for checks, # for checkmates, 1-0 / 0-1 / 1/2-1/2 for game end
       * </pre>
       *
       * <code>optional string move = 1;</code>
       */
      public Builder clearMove() {
        
        move_ = getDefaultInstance().getMove();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * the move in Algebraic Notation, + for checks, # for checkmates, 1-0 / 0-1 / 1/2-1/2 for game end
       * </pre>
       *
       * <code>optional string move = 1;</code>
       */
      public Builder setMoveBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        move_ = value;
        onChanged();
        return this;
      }

      private Object resultingState_ = "";
      /**
       * <pre>
       * the state after the move in Forsyth-Edwards Notation
       * </pre>
       *
       * <code>optional string resulting_state = 2;</code>
       */
      public String getResultingState() {
        Object ref = resultingState_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          resultingState_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       * the state after the move in Forsyth-Edwards Notation
       * </pre>
       *
       * <code>optional string resulting_state = 2;</code>
       */
      public com.google.protobuf.ByteString
          getResultingStateBytes() {
        Object ref = resultingState_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          resultingState_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * the state after the move in Forsyth-Edwards Notation
       * </pre>
       *
       * <code>optional string resulting_state = 2;</code>
       */
      public Builder setResultingState(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        resultingState_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * the state after the move in Forsyth-Edwards Notation
       * </pre>
       *
       * <code>optional string resulting_state = 2;</code>
       */
      public Builder clearResultingState() {
        
        resultingState_ = getDefaultInstance().getResultingState();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * the state after the move in Forsyth-Edwards Notation
       * </pre>
       *
       * <code>optional string resulting_state = 2;</code>
       */
      public Builder setResultingStateBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        resultingState_ = value;
        onChanged();
        return this;
      }

      private boolean lastMoveErrored_ ;
      /**
       * <code>optional bool last_move_errored = 3;</code>
       */
      public boolean getLastMoveErrored() {
        return lastMoveErrored_;
      }
      /**
       * <code>optional bool last_move_errored = 3;</code>
       */
      public Builder setLastMoveErrored(boolean value) {
        
        lastMoveErrored_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool last_move_errored = 3;</code>
       */
      public Builder clearLastMoveErrored() {
        
        lastMoveErrored_ = false;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:se.kth.inda17plusplus.Move)
    }

    // @@protoc_insertion_point(class_scope:se.kth.inda17plusplus.Move)
    private static final Move DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Move();
    }

    public static Move getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Move>
        PARSER = new com.google.protobuf.AbstractParser<Move>() {
      public Move parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new Move(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Move> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Move> getParserForType() {
      return PARSER;
    }

    public Move getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_se_kth_inda17plusplus_Move_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_se_kth_inda17plusplus_Move_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n se/kth/inda17plusplus/move.proto\022\025se.k" +
      "th.inda17plusplus\"H\n\004Move\022\014\n\004move\030\001 \001(\t\022" +
      "\027\n\017resulting_state\030\002 \001(\t\022\031\n\021last_move_er" +
      "rored\030\003 \001(\010b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_se_kth_inda17plusplus_Move_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_se_kth_inda17plusplus_Move_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_se_kth_inda17plusplus_Move_descriptor,
        new String[] { "Move", "ResultingState", "LastMoveErrored", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
