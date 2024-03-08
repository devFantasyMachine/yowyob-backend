package cm.yowyob.auth.app.domain.model.code;

public abstract class CodeGenerator<T extends Code> {

    private final Integer defaultLength;

    protected CodeGenerator(Integer defaultLength){
        this.defaultLength = defaultLength;
    }

    protected abstract T internalGenerateRandom(Integer length);

    final public T generate(Integer length){

        if (length == null || length < 1 )
            throw new IllegalArgumentException();

        return internalGenerateRandom(length);
    }

    final public T generate(){
        return generate(defaultLength);
    }


}

