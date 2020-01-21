package com.bpshparis.wa;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class TestParser {

    public final static ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    public static void main(final String[] args) throws IOException {

    List<String> prenoms = Arrays.asList("Alain", "Aurélie", "Bruno", "Charles", "Laure", "Maurice", "Michel", "Monique", "Sylvie", "Thierry", "Veronique");

    System.out.println(Tools.toJSON(prenoms));

    StandardEvaluationContext context = new StandardEvaluationContext();
    context.setVariable("prenoms", prenoms);
    List<?> startWithM = EXPRESSION_PARSER.parseExpression("#prenoms.?[startsWith('M')]").getValue(
        context, List.class);
    System.out.println(startWithM);

    String s = EXPRESSION_PARSER.parseExpression("T(String).join(', ', #prenoms)").getValue(context, String.class);
    System.out.println(s);

    List<Float> floats = Arrays.asList(1.3f, 2.2f, 3.0f, 4.0f, 5.0f);
    context.setVariable("floats", floats);

    String chaine = "1.3 + 2.2 + 3.0 + 4.0 + 4.0";

    double d = (double) EXPRESSION_PARSER.parseExpression(chaine).getValue();
    System.out.println(d);

    


    List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5);
    List<Long> longs = Arrays.asList(1L, 2L, 3L, 4L, 5L);
    List<Double> doubles = Arrays.asList(1.2d, 2.3d, 3.0d, 4.0d, 5.0d);
    // List<Float> floats = Arrays.asList(1.3f, 2.2f, 3.0f, 4.0f, 5.0f);

    long intSum = ints.stream()
            .mapToLong(Integer::longValue)
            .sum();

    long longSum = longs.stream()
            .mapToLong(Long::longValue)
            .sum();

    double doublesSum = doubles.stream()
            .mapToDouble(Double::doubleValue)
            .sum();

    double floatsSum = floats.stream()
            .mapToDouble(Float::doubleValue)
            .sum();

    System.out.println(String.format(
            "Integers: %s, Longs: %s, Doubles: %s, Floats: %s",
            intSum, longSum, doublesSum, floatsSum));
    

    System.out.println(Tools.toJSON(floats));

    chaine = "2.1 + 2.3";

    System.out.println(String.format("%.2f", EXPRESSION_PARSER.parseExpression(chaine).getValue()));

    // <? $compteur = 0 ?>
    // <? $total = 0 ?>
    // <? $compteur < $floats.size() ? $total = $floats[$compteur] : null ?>

    double[] da = {1.1, 2.2, 3.3, 4.4};

    System.out.println(Arrays.stream(da).sum());

  }
}