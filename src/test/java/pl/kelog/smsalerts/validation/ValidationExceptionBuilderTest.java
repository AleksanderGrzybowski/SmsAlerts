package pl.kelog.smsalerts.validation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;


public class ValidationExceptionBuilderTest {
    
    @Test
    public void should_build_exception_without_errors() {
        ValidationException exception = ValidationException.builder().build();
        
        assertThat(exception.errors).hasSize(0);
    }
    
    @Test
    public void should_build_exception_with_two_errors_with_chaining_adds() {
        ValidationException exception = ValidationException.builder()
                .add("a", "b")
                .add("c", "d")
                .build();
        
        assertThat(exception.errors).hasSize(2);
        assertThat(exception.errors.get(0).field).isEqualTo("a");
        assertThat(exception.errors.get(0).message).isEqualTo("b");
        assertThat(exception.errors.get(1).field).isEqualTo("c");
        assertThat(exception.errors.get(1).message).isEqualTo("d");
    }
    
    @Test
    public void should_build_exception_without_errors_if_predicate_is_true() {
        ValidationException exception = ValidationException.builder()
                .add(true, "a", "b")
                .build();
        
        assertThat(exception.errors).hasSize(0);
    }
    
    @Test
    public void should_build_exception_with_error_if_predicate_is_false() {
        ValidationException exception = ValidationException.builder()
                .add(false, "a", "b")
                .build();
        assertThat(exception.errors).hasSize(1);
    }
    
    @Test
    public void should_not_throw_if_there_are_no_errors() {
        try {
            ValidationException.builder().throwIfErrors();
        } catch (ValidationException e) {
            fail();
        }
    }
    
    @Test(expected = ValidationException.class)
    public void should_throw_if_there_are_errors() {
        ValidationException.builder().add("a", "b").throwIfErrors();
    }
}
