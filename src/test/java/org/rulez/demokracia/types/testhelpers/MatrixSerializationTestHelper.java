package org.rulez.demokracia.types.testhelpers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.rulez.demokracia.pdengine.beattable.Matrix;

public final class MatrixSerializationTestHelper {

  @SuppressWarnings("unchecked")
  public static Matrix<String, Integer> deserializeMatrix(final byte[] byteArray)
      throws IOException, ClassNotFoundException {
    InputStream inputStream = new ByteArrayInputStream(byteArray);
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    return (Matrix<String, Integer>) objectInputStream.readObject();
  }

  public static void serializeMatrix(final OutputStream outputStream, final Matrix<?, ?> theMatrix)
      throws IOException {
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(theMatrix);
    objectOutputStream.flush();
    objectOutputStream.close();
  }
}
