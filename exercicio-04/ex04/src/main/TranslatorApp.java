import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TranslatorApp {

    // CHAVE DE ASSINATURA 
    private static final String KEY = "1TlWxz29Hk41ujIPmf9qHUBg9wcfsHTz0HRCm8K2zaF3jbblDVatJQQJ99BJACZoyfiXJ3w3AAAbACOGP6nw"; 
    
    // ENDPOINT BASE DO SERVIÇO DE TRADUÇÃO
    private static final String ENDPOINT = "https://api.cognitive.microsofttranslator.com/"; 
    
    // REGIAO/LOCALIZAÇÃO 
    private static final String LOCATION = "brazilsouth"; 
    
    // Texto para tradução
    private static final String TEXTO_ORIGINAL = "Eu criei o meu primeiro serviço cognitivo no Azure e ele está funcionando!"; 
    private static final String FROM = "pt"; // Idioma de origem
    private static final String TO = "en";   // Idioma de destino

    public static void main(String[] args) {
        
        System.out.println("Iniciando a tradução...");
        System.out.println("Texto de entrada: " + TEXTO_ORIGINAL);

        try {
            // Constrói a URL da requisição 
            String url = ENDPOINT + "/translate?api-version=3.0&from=" + FROM + "&to=" + TO;
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            // 2. Configura a requisição 
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Ocp-Apim-Subscription-Key", KEY);
            con.setRequestProperty("Ocp-Apim-Subscription-Region", LOCATION); // Necessário para autenticação
            con.setDoOutput(true);

            // Constrói e envia o corpo da requisição JSON
            // O corpo deve ser um array JSON com um objeto 'Text'
            String jsonInputString = String.format("[{\"Text\": \"%s\"}]", TEXTO_ORIGINAL);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("UTF-8");
                os.write(input, 0, input.length);			
            }
            
            // Lê a resposta
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Processa a resposta JSON 
            JsonArray responseArray = new Gson().fromJson(response.toString(), JsonArray.class);
            
            // A resposta é um array, pegamos o primeiro resultado
            if (responseArray.size() > 0) {
                JsonObject firstResult = responseArray.get(0).getAsJsonObject();
                
                // Extrai a tradução do objeto 'translations'
                String traducao = firstResult.getAsJsonArray("translations")
                                             .get(0).getAsJsonObject()
                                             .get("text").getAsString();
                
                // Imprime o resultado final 
                System.out.println("\n--- RESULTADO DA CHAMADA AZURE TRANSLATOR ---");
                System.out.println("Tradução Recebida (" + TO + "): " + traducao);
            } else {
                System.out.println("Nenhuma tradução retornada pelo serviço.");
            }
            
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao chamar o serviço (Verifique Chave e Região!): " + e.getMessage());
            e.printStackTrace();
        }
    }
}