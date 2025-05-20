package es.dam1.gestropeliculas.baseDeDatos;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLManager {

    /**
     *
     * Escribe un objeto en un archivo XML utilizando JAXB.
     *
     * @param objeto   Objeto a serializar.
     * @param fileName Nombre del archivo XML de destino.
     * @return true si la operación se realizó correctamente, false en caso contrario.
     */
    public static <T> boolean writeXML(T objeto, String fileName) {
        boolean result = false;
        try {
            JAXBContext context = JAXBContext.newInstance(objeto.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(objeto, new File(fileName));
            result = true;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     *
     * Lee un archivo XML y lo convierte en un objeto utilizando JAXB.
     *
     * @param objeto   Objeto de referencia para la clase a deserializar.
     * @param fileName Nombre del archivo XML origen.
     * @return Objeto deserializado a partir del XML.
     */
    public static <T> T readXML(T objeto, String fileName) {
        T objetos = null;
        try {
            JAXBContext context = JAXBContext.newInstance(objeto.getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            objetos = (T) unmarshaller.unmarshal(new File(fileName));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return objetos;
    }
}
