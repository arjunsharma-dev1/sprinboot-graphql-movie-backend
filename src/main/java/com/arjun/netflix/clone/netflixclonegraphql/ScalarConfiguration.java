package com.arjun.netflix.clone.netflixclonegraphql;


import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class ScalarConfiguration {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder.scalar(dateScalar()).build();
    }

    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("Java 8 LocalDate")
                .coercing(new Coercing<LocalDate, String>() {
                    @Override
                    public @Nullable String serialize(@Nullable Object dataFetcherResult,
                                                      @Nullable GraphQLContext graphQLContext,
                                                      @Nullable Locale locale) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof LocalDate localDate) {
                            return localDate.toString();
                        }
                        throw new CoercingSerializeException();
                    }

                    @Override
                    public @Nullable LocalDate parseValue(@Nullable Object input,
                                                          @Nullable GraphQLContext graphQLContext,
                                                          @Nullable Locale locale) throws CoercingParseValueException {
                        if (input instanceof String dateString) {
                            return LocalDate.parse(dateString);
                        }
                        throw new CoercingParseValueException();
                    }

                    @Override
                    public @Nullable LocalDate parseLiteral(@Nullable Value<?> input,
                                                            @Nullable CoercedVariables variables,
                                                            @Nullable GraphQLContext graphQLContext,
                                                            @Nullable Locale locale) throws CoercingParseLiteralException {
                        if (input instanceof StringValue inputStringValue) {
                            return LocalDate.parse(inputStringValue.getValue());
                        }
                        throw new CoercingParseLiteralException();
                    }

                    @Override
                    public @Nullable Value<?> valueToLiteral(@Nullable Object input,
                                                             @Nullable GraphQLContext graphQLContext,
                                                             @Nullable Locale locale) {
                        if (input instanceof String inputString) {
                            return StringValue.of(inputString);
                        }
                        throw new CoercingParseValueException();
                    }
                }).build();
    }

}