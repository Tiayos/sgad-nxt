package ec.edu.ups.sgadnuxt.utils;

public enum Empresa {
    RECTORADO(13, 170L),
    EL_VECINO(6, 170L),
    CAMPUS_MARIA_AUXILIADORA(14, 3849L),
    CAMPUS_SUR(11, 3357L),
    CAMPUS_GIRON(9, 481L),
    CAMPUS_CENTENARIO(8, 3633L);

    private final int sedeCode;
    private final Long perCodigo;

    Empresa(int sedeCode, Long perCodigo) {
        this.sedeCode = sedeCode;
        this.perCodigo = perCodigo;
    }

    public int getSedeCode() {
        return sedeCode;
    }

    public Long getPerCodigo() {
        return perCodigo;
    }

    public static Long getPerCodigoBySedeCode(int sedeCode) {
        for (Empresa mapping : values()) {
            if (mapping.getSedeCode() == sedeCode) {
                return mapping.getPerCodigo();
            }
        }
        return null;
    }
}