package dio.budgeting.application;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistTransactionUseCaseTest {

    @Test
    void should_reject_transaction_with_null_description() {
        TransactionRepository repository = new TransactionRepository() {
            @Override
            public Transaction save(Transaction transaction) {
                throw new AssertionError("não deveria persistir");
            }

            @Override
            public List<Transaction> findAllByCategory(Category category) {
                return List.of();
            }
        };

        var useCase = new PersistTransactionUseCase(repository);

        var input = new PersistTransactionInput(
                "Teste",
                0,
                Category.GROCERIES
        );

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(input));
    }

    @Test
    void should_reject_transaction_with_blank_description() {
        TransactionRepository repository = new TransactionRepository() {
            @Override
            public Transaction save(Transaction transaction) {
                throw new AssertionError("não deveria persistir");
            }

            @Override
            public List<Transaction> findAllByCategory(Category category) {
                return List.of();
            }
        };

        var useCase = new PersistTransactionUseCase(repository);

        var input = new PersistTransactionInput(
                "Teste",
                0,
                Category.GROCERIES
        );

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(input));
    }

    @Test
    void should_reject_transaction_with_zero_amount() {
        TransactionRepository repository = new TransactionRepository() {
            @Override
            public Transaction save(Transaction transaction) {
                throw new AssertionError("não deveria persistir");
            }

            @Override
            public List<Transaction> findAllByCategory(Category category) {
                return List.of();
            }
        };

        var useCase = new PersistTransactionUseCase(repository);

        var input = new PersistTransactionInput(
                "Teste",
                0,
                Category.GROCERIES
        );

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(input));
    }

    @Test
    void should_reject_transaction_with_less_than_zero_amount() {
        TransactionRepository repository = new TransactionRepository() {
            @Override
            public Transaction save(Transaction transaction) {
                throw new AssertionError("não deveria persistir");
            }

            @Override
            public List<Transaction> findAllByCategory(Category category) {
                return List.of();
            }
        };

        var useCase = new PersistTransactionUseCase(repository);

        var input = new PersistTransactionInput(
                "Teste",
                -1,
                Category.GROCERIES
        );

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(input));
    }

    @Test
    void should_reject_transaction_with_null_category() {
        TransactionRepository repository = new TransactionRepository() {
            @Override
            public Transaction save(Transaction transaction) {
                throw new AssertionError("não deveria persistir");
            }

            @Override
            public List<Transaction> findAllByCategory(Category category) {
                return List.of();
            }
        };

        var useCase = new PersistTransactionUseCase(repository);

        var input = new PersistTransactionInput(
                "Teste",
                10,
                null
        );

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(input));
    }
}
