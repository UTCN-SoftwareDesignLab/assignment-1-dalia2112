package repository.account;


public abstract class AccountRepositoryDecorator implements AccountRepository{
    protected AccountRepository decoratedRepository;

    public AccountRepositoryDecorator(AccountRepository decoratedRepository) {
        this.decoratedRepository = decoratedRepository;
    }
}
