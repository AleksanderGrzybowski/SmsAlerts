package pl.kelog.smsalerts.validation

import spock.lang.Specification

class ValidationExceptionBuilderSpec extends Specification {

    void 'should build exception without errors'() {
        when:
        ValidationException exception = ValidationException.builder().build()

        then:
        exception.errors.empty
    }

    void 'should build exception with two errors with chaining adds'() {
        when:
        ValidationException exception = ValidationException.builder()
                .add('a', 'b')
                .add('c', 'd')
                .build()

        then:
        exception.errors.size() == 2
        exception.errors[0].field == 'a'
        exception.errors[0].message == 'b'
        exception.errors[1].field == 'c'
        exception.errors[1].message == 'd'
    }
    
    void 'should build exception without errors if predicate is true'() {
        when:
        ValidationException exception = ValidationException.builder()
                .add(true, 'a', 'b')
                .build()

        then:
        exception.errors.empty
    }
    
    void 'should build exception with error if predicate is false'() {
        when:
        ValidationException exception = ValidationException.builder()
                .add(false, 'a', 'b')
                .build()

        then:
        exception.errors.size() == 1
    }
    
    void 'should not throw if there are no errors'() {
        when:
        ValidationException.builder().throwIfErrors()
        
        then:
        notThrown ValidationException
    }

    void 'should throw if there are errors'() {
        when:
        ValidationException.builder().add('a', 'b').throwIfErrors()
        
        then:
        thrown ValidationException
    }
}
