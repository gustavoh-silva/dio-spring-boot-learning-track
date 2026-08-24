package dio.budgeting.application;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.application.output.TransactionOutput;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "persist-transaction", description = "Persiste uma nova transação financeira")
    public TransactionOutput execute(PersistTransactionInput input) {
        if (input.description() == null) {
            throw new IllegalArgumentException("A descrição não pode ser nula");
        } if (input.description().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode estar vazia");
        } else if (input.amount() <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser maior que zero.");
        } else if (input.category() == null) {
            throw new IllegalArgumentException("A categoria não pode ser nula");
        }

        var transaction = transactionRepository.save(
                new Transaction(input.description(), input.amount(), input.category()));

        return TransactionOutput.from(transaction);
    }
}
