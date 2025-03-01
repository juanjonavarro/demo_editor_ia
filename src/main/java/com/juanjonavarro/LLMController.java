package com.juanjonavarro;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LLMController {
    @Autowired
    ChatModel chatModel;

    @PostMapping("/ampliar")
    public Resultado ampliar(@RequestBody TextoContexto contexto) {
        ChatClient.Builder builder = ChatClient.builder(chatModel);

        String chatResponse = builder.build().prompt()
                .user(u -> u.text("""
                    Amplia y mejora el texto marcado como [TEXTO A AMPLIAR/MEJORAR].
                    Debes aumentarlo y mejorarlo aproximadamente al doble del tamaño original.
                    El texto de [TEXTO PREVIO] y de [TEXTO POSTERIOR] es solo
                    contexto, no debes modificarlo ni incluirlo en la salida.
                    La salida debe ser un sólo párrafo HTML <p>.
                    Solo el html, sin bloques fences de markdown.
                    ## [TEXTO PREVIO]:
                    {texto_previo}
                    ## [TEXTO POSTERIOR]:
                    {texto_posterior}
                    ## [TEXTO A AMPLIAR/MEJORAR]:
                    {texto}""")
                    .param("texto_previo", contexto.getTextoPrevio1()+"\n"+contexto.getTextoPrevio2())
                    .param("texto_posterior", contexto.getTextoSiguiente1()+"\n"+contexto.getTextoSiguiente2())
                    .param("texto", contexto.getTexto()))
                .call()
                .content();

        return new Resultado(chatResponse);
    }

    @PostMapping("/puntos")
    public Resultado puntos(@RequestBody TextoContexto contexto) {
        ChatClient.Builder builder = ChatClient.builder(chatModel);

        String chatResponse = builder.build().prompt()
                .user(u -> u.text("""
                    Convierte el texto indicado como [TEXTO A AMPLIAR/MEJORAR] en una serie de "bullets".
                    El texto de [TEXTO PREVIO] y de [TEXTO POSTERIOR] es solo
                    contexto, no debes modificarlo ni incluirlo en la salida.
                    La salida debe ser un solo párrafo HTML con una lista <ul> en su interior.
                    Solo el html, sin bloques fences de markdown.
                    ## [TEXTO PREVIO]:
                    {texto_previo}
                    ## [TEXTO POSTERIOR]:
                    {texto_posterior}
                    ## [TEXTO A AMPLIAR/MEJORAR]:
                    {texto}""")
                        .param("texto_previo", contexto.getTextoPrevio1()+"\n"+contexto.getTextoPrevio2())
                        .param("texto_posterior", contexto.getTextoSiguiente1()+"\n"+contexto.getTextoSiguiente2())
                        .param("texto", contexto.getTexto()))
                .call()
                .content();

        return new Resultado(chatResponse);
    }

    @PostMapping("/resumir")
    public Resultado resumir(@RequestBody TextoContexto contexto) {
        ChatClient.Builder builder = ChatClient.builder(chatModel);

        String chatResponse = builder.build().prompt()
                .user(u -> u.text("""
                    Resume y acorta el texto en [TEXTO A AMPLIAR/MEJORAR].
                    Debes resumirlo y acortarlo aproximadamente a la cuarta parte del tamaño original. Sólo las ideas básicas.
                    El texto de [TEXTO PREVIO] y de [TEXTO POSTERIOR] es solo
                    contexto, no debes modificarlo ni incluirlo en la salida.
                    La salida debe ser un sólo párrafo HTML <p>.
                    Solo el html, sin bloques fences de markdown.
                    ## [TEXTO PREVIO]:
                    {texto_previo}
                    ## [TEXTO POSTERIOR]:
                    {texto_posterior}
                    ## [TEXTO A AMPLIAR/MEJORAR]:
                    {texto}""")
                        .param("texto_previo", contexto.getTextoPrevio1()+"\n"+contexto.getTextoPrevio2())
                        .param("texto_posterior", contexto.getTextoSiguiente1()+"\n"+contexto.getTextoSiguiente2())
                        .param("texto", contexto.getTexto()))
                .call()
                .content();

        return new Resultado(chatResponse);
    }

    @PostMapping("/emojis")
    public Resultado emojis(@RequestBody TextoContexto contexto) {
        ChatClient.Builder builder = ChatClient.builder(chatModel);

        String chatResponse = builder.build().prompt()
                .user(u -> u.text("""
                    Añade emojis al texto en [TEXTO A AMPLIAR/MEJORAR].
                    Debes añadir emojis adecuados dentro del texto, sin modificar de ninguna otra manera el texto original.
                    El texto de [TEXTO PREVIO] y de [TEXTO POSTERIOR] es solo
                    contexto, no debes modificarlo ni incluirlo en la salida.
                    La salida debe ser un sólo párrafo HTML <p>.
                    Solo el html, sin bloques fences de markdown.
                    ## [TEXTO PREVIO]:
                    {texto_previo}
                    ## [TEXTO POSTERIOR]:
                    {texto_posterior}
                    ## [TEXTO A AMPLIAR/MEJORAR]:
                    {texto}""")
                        .param("texto_previo", contexto.getTextoPrevio1()+"\n"+contexto.getTextoPrevio2())
                        .param("texto_posterior", contexto.getTextoSiguiente1()+"\n"+contexto.getTextoSiguiente2())
                        .param("texto", contexto.getTexto()))
                .call()
                .content();

        return new Resultado(chatResponse);
    }

    @PostMapping("/ingles")
    public Resultado ingles(@RequestBody TextoContexto contexto) {
        ChatClient.Builder builder = ChatClient.builder(chatModel);

        String chatResponse = builder.build().prompt()
                .user(u -> u.text("""
                    Traduce al ingles el texto de [TEXTO A AMPLIAR/MEJORAR].
                    El texto de [TEXTO PREVIO] y de [TEXTO POSTERIOR] es solo
                    contexto, no debes modificarlo ni incluirlo en la salida.
                    La salida debe ser un sólo párrafo HTML <p>.
                    Solo el html, sin bloques fences de markdown.
                    ## [TEXTO PREVIO]:
                    {texto_previo}
                    ## [TEXTO POSTERIOR]:
                    {texto_posterior}
                    ## [TEXTO A AMPLIAR/MEJORAR]:
                    {texto}""")
                        .param("texto_previo", contexto.getTextoPrevio1()+"\n"+contexto.getTextoPrevio2())
                        .param("texto_posterior", contexto.getTextoSiguiente1()+"\n"+contexto.getTextoSiguiente2())
                        .param("texto", contexto.getTexto()))
                .call()
                .content();

        return new Resultado(chatResponse);
    }

}
