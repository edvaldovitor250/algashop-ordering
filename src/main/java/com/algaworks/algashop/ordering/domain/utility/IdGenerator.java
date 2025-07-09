package com.algaworks.algashop.ordering.domain.utility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.util.UUID;

import io.hypersistence.tsid.TSID;

public class IdGenerator {

    private static final TimeBasedEpochRandomGenerator timeBasedEpochRandomGenerator
            = Generators.timeBasedEpochRandomGenerator();

            private static final TSID.Factory tsidFactory = TSID.Factory.INSTANCE;

    private IdGenerator() {
    }

    public static TSID generateTsid() {
        return tsidFactory.generate();
    }

    public static UUID generateTimeBasedUUID() {
        return timeBasedEpochRandomGenerator.generate();
    }
}