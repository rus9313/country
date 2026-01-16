package com.example.country.service;

import com.example.country.domain.CountryGql;
import com.example.country.domain.CountryInputGql;
import com.example.grpc.country.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class GrpcCountryService extends CountryServiceGrpc.CountryServiceImplBase {
    private final CountryService countryService;

    public GrpcCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void addCounty(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        final CountryGql country = countryService.addCountryGql(
                new CountryInputGql(
                        request.getName(),
                        request.getCode())
        );
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(country.id().toString())
                        .setName(country.name())
                        .setCode(country.code())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void country(IdRequest request, StreamObserver<CountryResponse> responseObserver) {
        final CountryGql country = countryService.countryGqlById(request.getId());
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(country.id().toString())
                        .setName(country.name())
                        .setCode(country.code())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CountryRequest> streamingAddCounty(StreamObserver<CountResponse> responseObserver) {
        return new StreamObserver<CountryRequest>() {
            int count;

            @Override
            public void onNext(CountryRequest countryRequest) {
                countryService.addCountryGql(
                        new CountryInputGql(
                                countryRequest.getName(),
                                countryRequest.getCode())
                );
                count++;
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(CountResponse.newBuilder()
                        .setCount(count)
                        .build());
                responseObserver.onCompleted();
            }
        };
    }
}
