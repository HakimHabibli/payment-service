package org.example.paymentservice.rules;

import org.example.paymentservice.exception.NotFoundException;
import org.example.paymentservice.exception.NotNullException;
import org.example.paymentservice.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseRules<S>
{
    protected final JpaRepository<S, Long> _repository;

    protected BaseRules(JpaRepository<S, Long> repository) {
        _repository = repository;
    }

    public void checkNotNull(S obj)
    {
        if(obj == null) throw new NotNullException("obj isn't null");
    }

    public S findEntityIfExists(Long id) {
        return _repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity not found"));
    }
}
