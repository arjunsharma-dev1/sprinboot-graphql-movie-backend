package com.arjun.netflix.clone.netflixclonegraphql;


import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.IntValue;
import graphql.language.Value;
import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.LocalDate;
import java.util.Locale;

@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class ScalarConfiguration {
    /*@Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.Date);
    }*/
    @Bean
    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("Java 8 LocalDate")
                .coercing(new Coercing<LocalDate, Long>() {
                    @Override
                    public Long serialize(@NonNull Object dataFetcherResult,
                                          @NonNull GraphQLContext graphQLContext,
                                          @NonNull Locale locale) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof LocalDate localDate) {
                            return localDate.toEpochDay();
                        }
                        throw new CoercingSerializeException("Expected LocalDate type");
                    }

                    @Override
                    public LocalDate parseValue(@NonNull Object input,
                                                @NonNull GraphQLContext graphQLContext,
                                                @NonNull Locale locale) throws CoercingParseValueException {
                        if (input instanceof Long epochTime) {
                            return LocalDate.ofEpochDay(epochTime);
                        }
                        throw new CoercingParseValueException("Expected Long value");
                    }

                    @Override
                    public LocalDate parseLiteral(@NonNull Value<?> input,
                                                  @NonNull CoercedVariables variables,
                                                  @NonNull GraphQLContext graphQLContext,
                                                  @NonNull Locale locale) throws CoercingParseLiteralException {
                        if (input instanceof IntValue intValue) {
                            return LocalDate.ofEpochDay(intValue.getValue().longValue());
                        }
                        throw new CoercingParseLiteralException("Expected Long Literal");
                    }
                }).build();
    }
}
