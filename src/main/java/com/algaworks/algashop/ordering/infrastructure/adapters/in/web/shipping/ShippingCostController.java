@RestController
@RequiresArgsConstructor
public class ShippingCostController {

    private final ShippingApplicationService shippingApplicationService;

    @GetMapping("/shipping/cost/preview")
    public ShippingCostPreviewOutput previewCost(@RequestBody @Valid ShippingCostPreviewInput input) {
        return shippingApplicationService.previewCost(input);
    }

}
