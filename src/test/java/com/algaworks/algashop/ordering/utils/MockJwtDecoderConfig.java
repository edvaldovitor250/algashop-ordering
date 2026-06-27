public class MockJwtDecoderConfig{

    @Bean
    @Primary
    public JwtDecoder jwtDecoder() {
        return MockJwtDecoderFactory.createMockJwtDecoder();
    }
}